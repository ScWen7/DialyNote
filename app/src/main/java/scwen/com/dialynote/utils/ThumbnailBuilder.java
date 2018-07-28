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
package scwen.com.dialynote.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by YanZhenjie on 2017/10/15.
 */
public class ThumbnailBuilder {

    private Context mContext;

    private File mFileCacheDir;

    public ThumbnailBuilder(Context context) {
        this.mContext = context;
        this.mFileCacheDir = createCacheDir(mContext);
    }

    /**
     * Create a thumbnail for the image.
     *
     * @param imagePath image path.
     * @return thumbnail path.
     */
    @WorkerThread
    @Nullable
    public String createThumbnailForImage(String imagePath) {
        if (TextUtils.isEmpty(imagePath))
            return null;

        File inFile = new File(imagePath);
        if (!inFile.exists())
            return imagePath;

        File thumbnailFile = randomCacheImagePath(mFileCacheDir, imagePath);
        if (thumbnailFile.exists())
            return thumbnailFile.getAbsolutePath();

        Bitmap inBitmap = DefaultAlbumLoader.readImageFromPath(imagePath, 720, 1280);
        if (inBitmap == null) return imagePath;

        ByteArrayOutputStream compressStream = new ByteArrayOutputStream();
        int options = 100;
        inBitmap.compress(Bitmap.CompressFormat.JPEG, options, compressStream);

        while (compressStream.toByteArray().length > 200 * 1024) {
            if (options <= 0) {
                break;
            }
            compressStream.reset();
            options -= 10;
            inBitmap.compress(Bitmap.CompressFormat.JPEG, options, compressStream);
        }

        try {
            compressStream.close();
        } catch (IOException ignored) {
        }

        try {
            //noinspection ResultOfMethodCallIgnored
            thumbnailFile.createNewFile();
        } catch (Exception ignored) {
            return imagePath;
        }

        FileOutputStream writeStream = null;
        try {
            writeStream = new FileOutputStream(thumbnailFile);
            writeStream.write(compressStream.toByteArray());
        } catch (Exception ignored) {
            return imagePath;
        } finally {
            if (writeStream != null) {
                try {
                    writeStream.flush();
                } catch (IOException ignored) {
                }
                try {
                    writeStream.close();
                } catch (IOException ignored) {
                }
            }
        }

        return thumbnailFile.getAbsolutePath();
    }

    /**
     * Create a thumbnail for the video.
     *
     * @param videoPath video path.
     * @return thumbnail path.
     */
    @WorkerThread
    @Nullable
    public String createThumbnailForVideo(String videoPath) {
        if (TextUtils.isEmpty(videoPath)) return null;

        File thumbnailFile = randomCacheImagePath(mFileCacheDir, videoPath);
        if (thumbnailFile.exists())
            return thumbnailFile.getAbsolutePath();

        try {
            //noinspection ResultOfMethodCallIgnored
            thumbnailFile.createNewFile();
        } catch (Exception ignored) {
            return null;
        }

        Bitmap bitmap = null;
        try {
            bitmap = readVideoFrameFromPath(mContext, videoPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(thumbnailFile));
        } catch (Exception ignored) {
            return null;
        } finally {
            if (bitmap != null && !bitmap.isRecycled())
                bitmap.recycle();
        }
        return thumbnailFile.getAbsolutePath();
    }

    private static File createCacheDir(Context context) {
        File rootDir = AlbumUtils.getAlbumRootPath(context);
        File cacheDir = new File(rootDir, "AlbumCache");
        if (cacheDir.exists() && cacheDir.isFile())
            //noinspection ResultOfMethodCallIgnored
            cacheDir.delete();
        if (!cacheDir.exists())
            //noinspection ResultOfMethodCallIgnored
            cacheDir.mkdirs();
        File noMediaFile = new File(cacheDir, ".nomedia");
        if (noMediaFile.exists() && noMediaFile.isDirectory())
            //noinspection ResultOfMethodCallIgnored
            noMediaFile.delete();
        if (!noMediaFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                noMediaFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cacheDir;
    }

    private static File randomCacheImagePath(File cacheDir, String filePath) {
        String outFilePath = AlbumUtils.getMD5ForString(filePath) + ".jpg";
        return new File(cacheDir, outFilePath);
    }

    public static Bitmap readVideoFrameFromPath(Context context, String filePath) throws Exception {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND);

        if (bitmap == null) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(context, Uri.fromFile(new File(filePath)));
            bitmap = retriever.getFrameAtTime();
        }
        return bitmap;
    }
}