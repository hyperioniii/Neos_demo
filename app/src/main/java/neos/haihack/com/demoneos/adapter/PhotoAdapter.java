package neos.haihack.com.demoneos.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import neos.haihack.com.demoneos.R;

/**
 * Created by LinNH on 5/31/2016.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ItemGridView> {

    public List<String> listImg = new ArrayList<>();
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    public Context activity;

    public PhotoAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setData(List<String> data) {
        listImg.clear();
        listImg.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ItemGridView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new ItemGridView(view);
    }

    @Override
    public void onBindViewHolder(ItemGridView holder, int position) {
        ItemGridView programViewHolder = (ItemGridView) holder;
        programViewHolder.setData(listImg);
    }

    @Override
    public int getItemCount() {
        return listImg.size();
    }

    public class ItemGridView extends RecyclerView.ViewHolder {
        public ImageView imgView;

        public ItemGridView(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.img_show);
        }

        public void setData(final List<String> listImg) {
            Log.d(",", "============ list sixe--------" + listImg.size());
            Glide.with(itemView.getContext()).load(listImg.get(getAdapterPosition())).into(imgView);

            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    zoomImageFromThumb(view, listImg.get(getAdapterPosition()));
                }
            });
        }
    }

    private void zoomImageFromThumb(final View thumbView, String imageResId) {
        // If there's an animation in progress, cancel it immediately and
        // proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) ((Activity) activity)
                .findViewById(R.id.expanded_image);
        final LinearLayout linearLayout = (LinearLayout) ((Activity) activity)
                .findViewById(R.id.layout_hai);

        Glide.with(activity).load(imageResId).into(expandedImageView);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the
        // final bounds are the global visible rectangle of the container view.
        // Also
        // set the container view's offset as the origin for the bounds, since
        // that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        ((Activity) activity).findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the
        // "center crop" technique. This prevents undesirable stretching during
        // the animation.
        // Also calculate the start scaling factor (the end scaling factor is
        // always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
                .width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        thumbView.setAlpha(0f);
//        expandedImageView.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the
        // top-left corner of
        // the zoomed-in view (the default is the center of the view).
        linearLayout.setPivotX(0f);
        linearLayout.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        mShortAnimationDuration = 500;

        AnimatorSet set = new AnimatorSet();
        set.play(
                ObjectAnimator.ofFloat(linearLayout, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(linearLayout, View.Y, 0, 0))
//                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(linearLayout, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(linearLayout, View.SCALE_Y,
                        startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the
        // original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their
                // original values.
                mShortAnimationDuration = 100;
                AnimatorSet set = new AnimatorSet();
                set.play(
                        ObjectAnimator.ofFloat(linearLayout, View.X,
                                startBounds.left))
                        .with(ObjectAnimator.ofFloat(linearLayout, View.Y,
                                startBounds.top))
                        .with(ObjectAnimator.ofFloat(linearLayout,
                                View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator.ofFloat(linearLayout,
                                View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        linearLayout.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        linearLayout.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}
