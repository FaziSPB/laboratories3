package eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelBuilder;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelProperties;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannelPropertyKey;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.spy.TestSpySocialChannel;

public class ChainCensorDecoratorTest extends AbstractSocialChanneldDecoratorTest {

	@Test
	public void testChainCensorWithOtherDecorators() {
		SocialChannelBuilder builder = createTestSpySocialChannelBuilder();
		SocialChannelProperties props = new SocialChannelProperties().putProperty(SocialChannelPropertyKey.NAME, TestSpySocialChannel.NAME);
		SocialChannel channel = builder.
				with(new WordCensor(Arrays.asList("Microsoft"))).
				with(new MessageTruncator(20)).
				with(new URLAppender("http://jpereira.eu")).
				getDecoratedChannel(props);

		channel.deliverMessage("Microsoft Windows is great!");

		TestSpySocialChannel spyChannel = (TestSpySocialChannel) builder.buildChannel(props);
		assertEquals("### Windows is gr... http://jpereira.eu", spyChannel.lastMessagePublished());
	}
}
