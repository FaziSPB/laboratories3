package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeCodeForUnlockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeStateOfLockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotUnlockDoorException;

/**
 * Object adapter: wraps a ThirdPartyDoor instance and delegates to it.
 */
public class ThirdPartyDoorObjectAdapter implements Door {

	private final ThirdPartyDoor door = new ThirdPartyDoor();

	@Override
	public void open(String code) throws IncorrectDoorCodeException {
		try {
			door.unlock(code);
			door.setState(ThirdPartyDoor.DoorState.OPEN);
		} catch (CannotUnlockDoorException e) {
			throw new IncorrectDoorCodeException(e);
		} catch (CannotChangeStateOfLockedDoor e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void close() {
		try {
			door.setState(ThirdPartyDoor.DoorState.CLOSED);
			door.lock();
		} catch (CannotChangeStateOfLockedDoor e) {
			// a locked door is already closed
		}
	}

	@Override
	public boolean isOpen() {
		return door.getState() == ThirdPartyDoor.DoorState.OPEN;
	}

	@Override
	public void changeCode(String oldCode, String newCode, String newCodeConfirmation)
			throws IncorrectDoorCodeException, CodeMismatchException {
		ThirdPartyDoor.LockStatus before = door.getLockStatus();
		try {
			door.unlock(oldCode);
		} catch (CannotUnlockDoorException e) {
			throw new IncorrectDoorCodeException(e);
		}
		try {
			if (!newCode.equals(newCodeConfirmation)) {
				throw new CodeMismatchException();
			}
			door.setNewLockCode(newCode);
		} catch (CannotChangeCodeForUnlockedDoor e) {
			throw new IllegalStateException(e);
		} finally {
			if (before == ThirdPartyDoor.LockStatus.LOCKED) {
				door.lock();
			}
		}
	}

	@Override
	public boolean testCode(String code) {
		ThirdPartyDoor.LockStatus before = door.getLockStatus();
		try {
			door.unlock(code);
		} catch (CannotUnlockDoorException e) {
			return false;
		}
		if (before == ThirdPartyDoor.LockStatus.LOCKED) {
			door.lock();
		}
		return true;
	}
}
