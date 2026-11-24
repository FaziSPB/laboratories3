package eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator;

import java.util.List;

import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;

/**
 * Decorator that replaces every censored word with "###".
 */
public class WordCensor extends SocialChannelDecorator {

	private List<String> censoredWords;

	public WordCensor(List<String> censoredWords) {
		this.censoredWords = censoredWords;
	}

	public WordCensor(List<String> censoredWords, SocialChannel decoratedChannel) {
		this.censoredWords = censoredWords;
		this.delegate = decoratedChannel;
	}

	@Override
	public void deliverMessage(String message) {
		for (String word : censoredWords) {
			message = message.replace(word, "###");
		}
		delegate.deliverMessage(message);
	}
}
