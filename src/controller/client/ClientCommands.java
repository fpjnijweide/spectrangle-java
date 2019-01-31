package controller.client;

import model.Board;
import model.Piece;
import model.TileBag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ClientCommands {

    public static void setTiles(String[] args, Client clientObject) {
        String[] middleArgs = Arrays.copyOfRange(args, 0, args.length - 2);
        for (int peerNr = 0; peerNr < middleArgs.length / 5; peerNr++) {
            String name = middleArgs[peerNr * 5];
            if (name.equals(clientObject.getName())) {

                String[] tileArgs = Arrays.copyOfRange(middleArgs, (peerNr * 5) + 1, (peerNr * 5) + 5);
                clientObject.setClientTiles(Arrays.asList(tileArgs));


            }
        }
    }

    public static void makeBoard(Client clientObject) {
        clientObject.setBoard(new Board());
    }


    // prints other people's tiles in a shortened way
    public static void otherTiles(String[] args, Client clientObject) {
        clientObject.getPrinter().println("Other players have tiles:");
        clientObject.setOtherTileList(new ArrayList<>());
        String[] middleArgs = Arrays.copyOfRange(args, 0, args.length - 3);
        for (int peerNr = 0; peerNr < middleArgs.length / 5; peerNr++) {
            String name = middleArgs[peerNr * 5];
            if (!name.equals(clientObject.getName())) {
                String[] printArgs = Arrays.copyOfRange(args, peerNr * 5 + 1, peerNr * 5 + 5);
                clientObject.getOtherTileList().add(Arrays.asList(printArgs));
                String printString = String.join(" ", printArgs);
                printString = name + ": " + printString;


                clientObject.getPrinter().println(printString);
            }
        }


    }

    public static List<String> getClientTiles(Client clientObject) {
        return clientObject.getClientTiles();
    }


    public static TileBag generateBag(List<String> stringList) {
        TileBag result = new TileBag(4);
        for (String s : stringList) {
            result.addPiece(new Piece(s));
        }
        return result;
    }

    public static String bestMove(TileBag tileBag, Client clientObject) {
        int result = 0;
        Piece bestPiece = null;
        int bestPos = 0;
        Iterator itr = tileBag.getBag().iterator();

        while (itr.hasNext()) {
            Piece piece = (Piece) itr.next();
            List<Piece> pieceRotations = new ArrayList<>();
            pieceRotations.add(piece);
            pieceRotations.add(piece.getRotated());
            pieceRotations.add(piece.getRotated2x());
            for (Piece rotatedPiece : pieceRotations) {
                int i = 0;
                while (i < 36) {
                    int thisScore = clientObject.getBoard().getPotentialMoveScore(i, rotatedPiece);
                    if (thisScore > result) {
                        result = thisScore;
                        bestPiece = rotatedPiece;
                        bestPos = i;
                    }
                    i++;
                }
            }

        }

        return "place " + bestPiece.toString() + " on " + bestPos;
    }

    private static String randomMove(TileBag tileBag, Client clientObject) {
        Iterator itr = tileBag.getBag().iterator();

        while (itr.hasNext()) {
            Piece piece = (Piece) itr.next();
            List<Piece> pieceRotations = new ArrayList<>();
            pieceRotations.add(piece);
            pieceRotations.add(piece.getRotated());
            pieceRotations.add(piece.getRotated2x());
            for (Piece rotatedPiece : pieceRotations) {
                int i = 0;
                while (i < 36) {
                    if (clientObject.getBoard().isValidMove(i, rotatedPiece)) {
                        return "place " + rotatedPiece.toString() + " on " + i;

                    }
                    i++;

                }
            }

        }
        return "";
    }


    public static void aiTurn(Client clientObject) {
        if (clientObject.getDifficulty() < 1) {
            clientObject.sendMessageToAll(randomMove(generateBag(clientObject.getClientTiles()), clientObject));
        } else if (clientObject.getDifficulty() < 2) {
            clientObject.sendMessageToAll(bestMove(generateBag(clientObject.getClientTiles()), clientObject));
        }

        // TODO add calculation based on enemy tiles and future board states
        // TODO cap off this calculation after difficulty in seconds has passed
    }

    public static void aiSkip(Client clientObject) {
        double random = Math.random();


        if (random <= 0.5) {
            clientObject.sendMessageToAll("skip");
        } else {
            TileBag tiles = generateBag(clientObject.getClientTiles());
            Piece tile;
            double secondRandom = Math.random();
            if (secondRandom < 0.25) {
                tile = tiles.viewPiece(0);
            } else if (secondRandom < 0.5) {
                tile = tiles.viewPiece(1);
            } else if (secondRandom < 0.75) {
                tile = tiles.viewPiece(2);
            } else {
                tile = tiles.viewPiece(3);
            }
            clientObject.sendMessageToAll("exchange " + tile.toString());
        }

    }
}
