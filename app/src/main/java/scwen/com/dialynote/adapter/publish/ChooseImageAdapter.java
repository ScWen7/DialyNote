/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package scwen.com.dialynote.adapter.publish;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import scwen.com.dialynote.R;

/**
 * <p>Image adapter.</p>
 * Created by Yan Zhenjie on 2016/10/30.
 */
public class ChooseImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_ADD = 1;
    private LayoutInflater mInflater;

    private Context mContext;
    private int itemSize;
    private OnItemClickListener mItemClickListener;
    private OnItemClickListener mAddImageListener;

    private List<String> mImages = new ArrayList<>();

    private int maxImgCount;


    private boolean addEnabled = false;

    private boolean preEnabled = true;

    public boolean isPreEnabled() {
        return preEnabled;
    }

    public void setPreEnabled(boolean preEnabled) {
        this.preEnabled = preEnabled;
    }

    public boolean isAddEnabled() {
        return addEnabled;
    }

    public void setAddEnabled(boolean addEnabled) {
        this.addEnabled = addEnabled;
    }

    public ChooseImageAdapter(Context context, int itemSize, int maxImgCount, OnItemClickListener itemClickListener) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.itemSize = itemSize;
        this.maxImgCount = maxImgCount;
        this.mItemClickListener = itemClickListener;
    }

    public void setmAddImageListener(OnItemClickListener mAddImageListener) {
        this.mAddImageListener = mAddImageListener;
    }

    public void notifyDataSetChanged(List<String> imagePathList) {
        this.mImages = imagePathList;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mImages.size() == maxImgCount) {
            return TYPE_IMAGE;
        } else if (position <= mImages.size() - 1) {
            return TYPE_IMAGE;
        } else {
            return TYPE_ADD;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_IMAGE: {
                return new ImageViewHolder(mInflater.inflate(R.layout.item_choose_image, parent, false), itemSize, mItemClickListener);
            }
            case TYPE_ADD:
            default: {
                return new AddViewHolder(mInflater.inflate(R.layout.item_choose_add, parent, false), itemSize, mAddImageListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_IMAGE: {
                ((ImageViewHolder) holder).setData(mImages.get(position));
                break;
            }
            case TYPE_ADD: {

                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = mImages == null ? 0 : mImages.size();

        return (count + 1) >= maxImgCount ? maxImgCount : (count + 1);
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private final int itemSize;
        private final OnItemClickListener mItemClickListener;

        private ImageView mIvImage;


        ImageViewHolder(View itemView, int itemSize, OnItemClickListener itemClickListener) {
            super(itemView);
            itemView.getLayoutParams().height = itemSize;

            this.itemSize = itemSize;
            this.mItemClickListener = itemClickListener;

            mIvImage = itemView.findViewById(R.id.iv_img);


        }

        public void setData(final String path) {

            Glide.with(mContext).load(path).override(itemSize, itemSize).into(mIvImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null && preEnabled) {
                        mItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }


    }

    private static class AddViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemClickListener mAddImageListener;


        AddViewHolder(View itemView, int itemSize, OnItemClickListener addImageListener) {
            super(itemView);
            itemView.getLayoutParams().height = itemSize;
            this.mAddImageListener = addImageListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mAddImageListener != null) {
                mAddImageListener.onItemClick(v, getAdapterPosition());
            }
        }
    }


    public interface OnItemClickListener {

        /**
         * When Item is clicked.
         *
         * @param view     item view.
         * @param position item position.
         */
        void onItemClick(View view, int position);
    }
}
