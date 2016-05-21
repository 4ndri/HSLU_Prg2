/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.net;

import battleship.engine.Engine;
import battleship.engine.actions.EngineAction;

/**
 *
 * @author Andri
 */
public class MessageProcessor extends Thread {

    Connection mConnection;

    public MessageProcessor(Connection aConnection) {
        mConnection = aConnection;
    }

    @Override
    public void run() {

        try {
            System.out.println("MessageProcessor started");
            while (!isInterrupted()) {
                Message message = mConnection.mailbox.waitForNextInboxMessage();
                System.out.println("processing message");
                processMessage(message);
            }

        } catch (Exception e) {
            System.out.println("Error in MessageProcessor");
            // Commuication problem
        }

        // Communication is broken. Interrupt both listener and sender threads
        mConnection.interrupt();
    }

    private boolean processMessage(Message message) {
        switch (message.type) {
            case 0://stanard message
                //console output
                System.out.println(message.textMessage);
                break;
            case 1://bomb
                MsgBomb bombMessage = (MsgBomb) message;
                System.out.println("You are bombed on field: X: " + bombMessage.bomb.x + " |  Y: " + bombMessage.bomb.y);
                mConnection.oponent.bombFromOponent(bombMessage.bomb);
                break;
            case 2://bombreport
                MsgBombReport reportMessage = (MsgBombReport) message;
                System.out.println("You hit your enemy with the bomb: bombOnship:" + reportMessage.bombReport.bombOnShip + " |  Ship is destroyed: " + reportMessage.bombReport.shipDestroyed + " |  game over: " + reportMessage.bombReport.gameOver);
                mConnection.oponent.bombReportFromOponent(reportMessage.bombReport);
                break;
            case 3://ready message
                mConnection.oponent.readyFromOponent();
            case 99:
                Engine.getEngine().pushAction(new EngineAction(99,message.textMessage));
                break;
            default:
                MsgError errorMessage = (MsgError) message;
                System.out.println(errorMessage.textMessage);
                break;
        }

        return false;
    }
}
