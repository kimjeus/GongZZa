package com.dongkyoo.gongzza.customViews.hashTagRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.customViews.hashTagView.HashTagView;
import com.dongkyoo.gongzza.databinding.ItemHashtagListBinding;
import com.dongkyoo.gongzza.vos.HashTag;

import java.util.ArrayList;
import java.util.List;

public class HashTagRecyclerAdapter extends RecyclerView.Adapter {

    private static final int TYPE_BODY = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    private List<HashTag> hashTagList;
    private List<Boolean> editModeList;
    private boolean isAppendable = true;
    private List<OnHashTagChangedListener> hashTagChangedListenerList;

    public interface OnHashTagChangedListener {
        void onChange();
    }

    public void setOnHashTagChangedListener(OnHashTagChangedListener listener) {
        hashTagChangedListenerList.add(listener);
    }

    public HashTagRecyclerAdapter(Context context) {
        hashTagChangedListenerList = new ArrayList<>();
        this.context = context;
        hashTagList = new ArrayList<>();
        editModeList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (isAppendable) {
            if (position == hashTagList.size())
                return TYPE_FOOTER;
            return TYPE_BODY;
        }

        return TYPE_BODY;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == TYPE_BODY) {
            return new ViewHolder(layoutInflater.inflate(R.layout.item_hashtag_list, parent, false));
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(layoutInflater.inflate(R.layout.item_footer_hashtag_list, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.setHashTag(hashTagList.get(position));
            viewHolder.hashTagView.setVisibility(View.VISIBLE);
            viewHolder.hashTagView.setEditMode(editModeList.get(position));
            viewHolder.hashTagView.setOnEditModeChangeListener(new HashTagView.OnEditModeChangeListener() {
                @Override
                public void onChange(boolean editMode) {
                    editModeList.set(position, editMode);
                }

                @Override
                public void onPinned() {
                    for (OnHashTagChangedListener listener : hashTagChangedListenerList) {
                        listener.onChange();
                    }
                }
            });

            viewHolder.hashTagView.setCloseListenerList(new HashTagView.OnCloseListener() {
                @Override
                public void onClose() {
                    editModeList.remove(position);
                    hashTagList.remove(position);
                    for (OnHashTagChangedListener listener : hashTagChangedListenerList) {
                        listener.onChange();
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return hashTagList.size() + (isAppendable ? 1 : 0);
    }

    public void setHashTagList(List<HashTag> hashTagList) {
        this.hashTagList = hashTagList;
        editModeList = new ArrayList<>();
        for (HashTag hashTag : hashTagList) {
            editModeList.add(false);
        }
        notifyDataSetChanged();
    }

    List<HashTag> getHashTagList() {
        return hashTagList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        HashTagView hashTagView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
        }

        void setHashTag(HashTag hashTag) {
            ItemHashtagListBinding binding = DataBindingUtil.bind(itemView);
            binding.setHashTag(hashTag);
            binding.executePendingBindings();

            hashTagView = binding.hashTagView;
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(@NonNull View itemView) {
            super(itemView);

            ImageButton addImageButton = itemView.findViewById(R.id.add_imageButton);
            addImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean alreadyExist = false;
                    for (boolean b : editModeList) {
                        if (b) {
                            alreadyExist = true;
                            break;
                        }
                    }

                    if (!alreadyExist) {
                        hashTagList.add(new HashTag());
                        editModeList.add(true);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public boolean isAppendable() {
        return isAppendable;
    }

    public void setAppendable(boolean appendable) {
        isAppendable = appendable;
        notifyDataSetChanged();
    }
}
