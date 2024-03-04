package br.ufal.ic.p2.wepayu.memento;

import br.ufal.ic.p2.wepayu.Exception.NaoComandoDesfazerException;
import br.ufal.ic.p2.wepayu.Exception.NaoComandoRefazerException;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.gerencia.Sistema;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class MementoCommands {
    private Stack<Memento> undoMementos = new Stack<Memento>();
    private Stack<Memento> redoMementos = new Stack<Memento>();

    public MementoCommands(){}

    public void backup() throws CloneNotSupportedException{
        this.undoMementos.push(Sistema.save());
        this.redoMementos.clear();
    }

    public void undo() throws NaoComandoDesfazerException {
        if(!undoMementos.isEmpty()) {

            Memento memento = undoMementos.pop();

            if(!undoMementos.isEmpty()) {
                redoMementos.push(memento);
            }

            Sistema.restore(memento);
        } else {
            throw new NaoComandoDesfazerException();
        }
    }

    public void redo() throws NaoComandoRefazerException {
        if(!redoMementos.isEmpty()) {
            Memento memento = redoMementos.pop();
            Sistema.restore(memento);
            undoMementos.push(memento);
        } else {
            throw new NaoComandoRefazerException();
        }
    }

    public Stack<Memento> getUndoMementos() {
        return this.undoMementos;
    }

    public Stack<Memento> getRedoMementos() {
        return this.redoMementos;
    }
}
