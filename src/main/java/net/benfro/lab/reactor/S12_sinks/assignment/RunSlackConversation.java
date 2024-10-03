package net.benfro.lab.reactor.S12_sinks.assignment;

import net.benfro.lab.reactor.common.RunUtilities;

public class RunSlackConversation {

    public static void main(String[] args) {
        SlackRoom room = new SlackRoom("reactor");

        SlackMember sam = new SlackMember("sam");
        SlackMember jake = new SlackMember("jake");
        SlackMember mike = new SlackMember("mike");

        room.addMember(sam);
        room.addMember(jake);

        sam.says("Hi all..");

        RunUtilities.sleep(4);

        jake.says("Hey!");
        sam.says("I simply wanted to say hi..");

        RunUtilities.sleep(4);

        room.addMember(mike);
        mike.says("Hey, guys. Glad to be here..");
    }
}
