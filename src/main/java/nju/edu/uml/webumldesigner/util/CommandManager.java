package nju.edu.uml.webumldesigner.util;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List undoList = new ArrayList();

    private  int undoCount = -1;

    public CommandManager() {
        undoCount = 10;
    }

    public void undo() {
        if (undoList.size() <= 0) {
            return;
        }

//        Command cmd = ((Command)(undoList.get(undoList.size() - 1)));
//        cmd.undo();
//
//        undoList.remove(cmd);
//        redoList.add(cmd);
    }

}
