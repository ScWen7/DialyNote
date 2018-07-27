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
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * <p>Adapter of preview the big picture.</p>
 * Created by yanzhenjie on 17-3-29.
 */
public class ImagePreviewAdapter extends PagerAdapter {


    private Context mContext;
    private List<String> mPreviewList;

    public ImagePreviewAdapter(Context context, List<String> previewList) {
        this.mContext = context;
        this.mPreviewList = previewList;
    }

    public void setData(List<String> mPreviewList) {
        this.mPreviewList = mPreviewList;
    }

    @Override
    public int getCount() {
        return mPreviewList == null ? 0 : mPreviewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView imageView = new PhotoView(mContext);

        String path = mPreviewList.get(position);

        Glide.with(mContext)
                .load(path)
                .into(imageView);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
