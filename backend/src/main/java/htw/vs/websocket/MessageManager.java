package htw.vs.websocket;

import htw.vs.data.Board;
import htw.vs.data.Message;
import htw.vs.data.MessageRepository;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * The type Message manager.
 */
@Component
public class MessageManager {
    private final MessageRepository messageRepository;

    private Map<Long, List<Message>> boardMessages;

    private MessageManager(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        generateBoardMessages();
        initialize();
    }

    void initialize() {
        new CleanerThread().start();
    }

    private void generateBoardMessages(){
        List<Message> msgs = messageRepository.findMessagesToDisplay();
        boardMessages = msgs.parallelStream().collect(Collector.of(
                    ConcurrentHashMap::new,
                    (map, m) -> map.computeIfAbsent(m.getBoard().getId(), k -> {
                        CopyOnWriteArrayList l = new CopyOnWriteArrayList<>();
                        l.add(m);
                        return l;
                    }),
                    (a, b) -> {
                        b.forEach(( key, set ) -> a.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>()).addAll(set));
                        return a;
                    }
        ));
    }


    public List<Message> addOrReplace(Message msg) {
        msg = messageRepository.save(msg);
        List<Message> msgs = boardMessages.get(msg.getBoard());
        if(msgs == null) {
            msgs = new CopyOnWriteArrayList<>();
            boardMessages.put(msg.getBoard().getId(), msgs);
        }
        Message finalMsg = msg;
        msgs.removeIf(m -> m.getId() == finalMsg.getId());
        if(finalMsg.isActive() && finalMsg.getTtl().after(new Timestamp(System.currentTimeMillis())))
            msgs.add(finalMsg);
        return msgs;
    }

    public List<Message> getAllByBoard(Board board) {
        return boardMessages.get(board);
    }


    class CleanerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                clean();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void clean() {
            for(List<Message> lm : boardMessages.values()){
                lm.removeIf(m -> m.getTtl().before(new Timestamp(System.currentTimeMillis())));
            }
        }
    }
}
