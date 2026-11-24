package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeCodeForUnlockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeStateOfLockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotUnlockDoorException;

/**
 * Class adapter: inherits ThirdPartyDoor and exposes it as a Door.
 */
public class ThirdPartyDoorAdaper extends ThirdPartyDoor implements Door {

	@Override
	public void open(String code) throws IncorrectDoorCodeException {
		try {
			unlock(code);
			setState(DoorState.OPEN);
		} catch (CannotUnlockDoorException e) {
			throw new IncorrectDoorCodeException(e);
		} catch (CannotChangeStateOfLockedDoor e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void close() {
		try {
			setState(DoorState.CLOSED);
			lock();
		} catch (CannotChangeStateOfLockedDoor e) {
			// a locked door is already closed
		}
	}

	@Override
	public boolean isOpen() {
		return getState() == DoorState.OPEN;
	}

	@Override
	public void changeCode(String oldCode, String newCode, String newCodeConfirmation)
			throws IncorrectDoorCodeException, CodeMismatchException {
		LockStatus before = getLockStatus();
		try {
			unlock(oldCode);
		} catch (CannotUnlockDoorException e) {
			throw new IncorrectDoorCodeException(e);
		}
		try {
			if (!newCode.equals(newCodeConfirmation)) {
				throw new CodeMismatchException();
			}
			setNewLockCode(newCode);
		} catch (CannotChangeCodeForUnlockedDoor e) {
			throw new IllegalStateException(e);
		} finally {
			if (before == LockStatus.LOCKED) {
				lock();
			}
		}
	}

	@Override
	public boolean testCode(String code) {
		LockStatus before = getLockStatus();
		try {
			unlock(code);
		} catch (CannotUnlockDoorException e) {
			return false;
		}
		if (before == LockStatus.LOCKED) {
			lock();
		}
		return true;
	}
}
