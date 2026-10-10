package net.wildyax.petprotection.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static class Server {

        public final ServerMedal ironMedal;
        public final ServerMedal goldMedal;
        public final ServerMedal diamondMedal;

        Server(ModConfigSpec.Builder builder) {
            builder.push("medals");
            ironMedal = new ServerMedal(builder, "iron_medal", true, false, false);
            goldMedal = new ServerMedal(builder, "gold_medal", true, true, false);
            diamondMedal = new ServerMedal(builder, "diamond_medal", true, true, true);
            builder.pop();
        }
    }

    public static class ServerMedal {

        public final ModConfigSpec.BooleanValue protectFromOwner;
        public final ModConfigSpec.BooleanValue protectFromTeam;
        public final ModConfigSpec.BooleanValue allowResurrection;

        ServerMedal(ModConfigSpec.Builder builder, String name, boolean protectFromOwnerDefault, boolean protectFromTeamDefault, boolean allowResurrectionDefault) {
            builder.push(name);
            protectFromOwner = builder
                .comment("The medal protects the pet from its owner's damage")
                .define("protect_from_owner", protectFromOwnerDefault);
            protectFromTeam = builder
                .comment("The medal protects the pet from its owner's teammates, or from all players if PVP is turned off")
                .define("protect_from_team", protectFromTeamDefault);
            allowResurrection = builder
                .comment("The pet drops a soul medal when it dies, to craft a resurrection pearl in an anvil")
                .define("allow_resurrection", allowResurrectionDefault);
            builder.pop();
        }
    }

    public static class Client {

        public final ClientMedal ironMedal;
        public final ClientMedal goldMedal;
        public final ClientMedal diamondMedal;

        Client(ModConfigSpec.Builder builder) {
            builder.push("medals");
            ironMedal = new ClientMedal(builder, "iron_medal");
            goldMedal = new ClientMedal(builder, "gold_medal");
            diamondMedal = new ClientMedal(builder, "diamond_medal");
            builder.pop();
        }
    }

    public static class ClientMedal {

        public final ModConfigSpec.BooleanValue renderOnPet;

        ClientMedal(ModConfigSpec.Builder builder, String name) {
            builder.push(name);
            renderOnPet = builder
                .comment("Display the medal on the pet")
                .define("render_on_pet", true);
            builder.pop();
        }
    }

    public static final ModConfigSpec SERVER_SPEC;
    public static final Server SERVER;
    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final Pair<Server, ModConfigSpec> serverPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER = serverPair.getLeft();
        SERVER_SPEC = serverPair.getRight();

        final Pair<Client, ModConfigSpec> clientPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT = clientPair.getLeft();
        CLIENT_SPEC = clientPair.getRight();
    }
}
