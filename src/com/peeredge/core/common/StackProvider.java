package com.peeredge.core.common;

import com.peeredge.core.common.impl.StackImpl;
import com.peeredge.core.unknown.MainActivity;

/**
 * Created by samson on 3/25/17.
 */
public class StackProvider {
    private static Stack _stack;
    public static Stack getStack()
    {
        if(_stack == null) {
            _stack = new StackImpl();
            _stack.setIncomingCallActivity(MainActivity.class);
        }
        return  _stack;
    }
}
