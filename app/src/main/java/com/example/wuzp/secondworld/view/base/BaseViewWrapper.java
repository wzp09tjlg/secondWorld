package com.example.wuzp.secondworld.view.base;


import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

@SuppressWarnings("All")
@UiThread
public abstract class BaseViewWrapper<B extends ViewDataBinding> {
    protected B binding;

    public BaseViewWrapper() {
        binding = null;
    }

    /**
     * @deprecated 避免空指针的控制与出现，可以在创建wrapper时直接创建对象后通过addBinding设置binding
     */
    @Deprecated
    public BaseViewWrapper(B binding) {
        this.binding = binding;
    }

    public void addBinding(@NonNull B binding) {
        this.binding = binding;
    }

    public abstract void release();
}
