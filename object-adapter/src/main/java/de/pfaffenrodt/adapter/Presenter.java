/*
 * Copyright (C) 2017 Dimitri Pfaffenrodt
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.pfaffenrodt.adapter;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A Presenter is used to generate {@link View}s and bind Objects to them on
 * demand. It is closely related to the concept of an {@link
 * android.support.v7.widget.RecyclerView.Adapter Adapter}, but is
 * not position-based.  The leanback framework implements the adapter concept using
 * {@link ObjectAdapter} which refers to a Presenter (or {@link PresenterSelector}) instance.
 *
 * <p>
 * Presenters should be stateless.  Presenters typically extend {@link ObjectAdapter.ViewHolder} to store all
 * necessary view state information, such as references to child views to be used when
 * binding to avoid expensive calls to {@link View#findViewById(int)}.
 * </p>
 *
 * <p>
 * A trivial Presenter that takes a string and renders it into a {@link
 * android.widget.TextView TextView}:
 *
 * <pre class="prettyprint">
 * public class StringTextViewPresenter extends Presenter {
 *     // This class does not need a custom ViewHolder, since it does not use
 *     // a complex layout.
 *
 *     {@literal @}Override
 *     public ViewHolder onCreateViewHolder(ViewGroup parent) {
 *         return new ViewHolder(new TextView(parent.getContext()));
 *     }
 *
 *     {@literal @}Override
 *     public void onBindViewHolder(ViewHolder viewHolder, Object item) {
 *         String str = (String) item;
 *         TextView textView = (TextView) viewHolder.mView;
 *
 *         textView.setText(item);
 *     }
 *
 *     {@literal @}Override
 *     public void onUnbindViewHolder(ViewHolder viewHolder) {
 *         // Nothing to unbind for TextView, but if this viewHolder had
 *         // allocated bitmaps, they can be released here.
 *     }
 * }
 * </pre>
 */
public abstract class Presenter{

    /**
     * @return id of layout that will be inflated in {@link #onCreateViewHolder(ViewGroup)} {@link #inflateItemLayout(ViewGroup)}
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * Creates a new {@link View}.
     */
    public ObjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent){
        View itemView = inflateItemLayout(parent);
        return onCreateViewHolder(itemView, parent);
    }

    public abstract ObjectAdapter.ViewHolder onCreateViewHolder(View itemView, ViewGroup parent);

    private View inflateItemLayout(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(getLayoutId(), parent, false);
    }
    /**
     * Binds a {@link View} to an item.
     */
    public abstract void onBindViewHolder(ObjectAdapter.ViewHolder viewHolder, Object item);

    /**
     * Unbinds a {@link View} from an item. Any expensive references may be
     * released here, and any fields that are not bound for every item should be
     * cleared here.
     */
    public void onUnbindViewHolder(ObjectAdapter.ViewHolder viewHolder){}

    /**
     * Called when a view created by this presenter has been attached to a window.
     *
     * <p>This can be used as a reasonable signal that the view is about to be seen
     * by the user. If the adapter previously freed any resources in
     * {@link #onViewDetachedFromWindow(ObjectAdapter.ViewHolder)}
     * those resources should be restored here.</p>
     *
     * @param holder Holder of the view being attached
     */
    public void onViewAttachedToWindow(ObjectAdapter.ViewHolder holder) {
    }
    /**
     * Called when a view created by this presenter has been detached from its window.
     *
     * <p>Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an presenter's views may choose to cache views offscreen while they
     * are not visible, attaching and detaching them as appropriate.</p>
     *
     * @param holder Holder of the view being detached
     */
    public void onViewDetachedFromWindow(ObjectAdapter.ViewHolder holder) {
    }

    /**
     * Called to set a click listener for the given view holder.
     *
     * The default implementation sets the click listener on the root view in the view holder.
     * If the root view isn't focusable this method should be overridden to set the listener
     * on the appropriate focusable child view(s).
     *
     * @param holder The view holder containing the view(s) on which the listener should be set.
     * @param listener The click listener to be set.
     */
    public void setOnClickListener(ObjectAdapter.ViewHolder holder, View.OnClickListener listener) {
        holder.itemView.setOnClickListener(listener);
    }
}
