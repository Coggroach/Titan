package com.coggroach.titan.tile;

/**
 * Created by TARDIS on 04/12/2014.
 */
public class TileAnimation
{
    private int animationIndex;
    private int animationLength;
    private int animationTickLength;
    private int animationTickIndex;
    private boolean hasAnimation;
    private boolean animationLoop;
<<<<<<< HEAD
    private boolean saveAnimation;
=======
>>>>>>> origin/ColourPalette
    private ITileAnimation animation;

    public TileAnimation()
    {
        this.animation = null;
        this.animationIndex = 0;
        this.hasAnimation = false;
        this.animationLength = 1;
        this.animationLoop = false;
        this.animationTickIndex = 0;
        this.animationTickLength = 1;
<<<<<<< HEAD
        this.saveAnimation = false;
=======
>>>>>>> origin/ColourPalette
    }

    public boolean hasAnimation()
    {
        return this.hasAnimation;
    }

    public float[] onAnimation(float[] mMVPMatrix)
    {
        if(this.animation != null)
        {
            return this.animation.onAnimation(this.animationIndex, mMVPMatrix);
        }
        return mMVPMatrix;
    }

    public void incAnimation()
    {
        boolean flag = this.animationIndex < this.animationLength;
        if(flag)
            this.animationIndex++;
        else if(this.animationLoop && !flag)
            this.animationIndex = 0;
        else if(!this.animationLoop && !flag)
        {
            this.hasAnimation = false;
<<<<<<< HEAD
            if(!this.loadLastAnimation()) {
                this.animationIndex = 0;
                this.animation = null;
            }
=======
            this.animationIndex = 0;
            this.animation = null;
>>>>>>> origin/ColourPalette
        }
    }

    public void incAnimationTick()
    {
<<<<<<< HEAD
        if(!loadLastAnimation()||this.animationTickIndex < this.animationTickLength)
            this.animationTickIndex++;
=======
        this.animationTickIndex++;
>>>>>>> origin/ColourPalette
    }

    public void setAnimation(ITileAnimation animation)
    {
        this.animation = animation;
        this.hasAnimation = true;
    }

    public int getAnimationIndex()
    {
        return animationIndex;
    }

    public void setAnimationIndex(int animationIndex)
    {
        this.animationIndex = animationIndex;
    }

    public void setAnimationLength(int animationLength)
    {
        this.animationLength = animationLength;
    }

    public void setAnimationLoop(boolean animationLoop)
    {
        this.animationLoop = animationLoop;
    }

    public int getAnimationTickLength() {
        return animationTickLength;
    }

    public void setAnimationTickLength(int animationTickLength) {
        this.animationTickLength = animationTickLength;
    }

    public int getAnimationTickIndex() {
        return animationTickIndex;
    }

    public void setAnimationTickIndex(int animationTickIndex) {
        this.animationTickIndex = animationTickIndex;
    }
<<<<<<< HEAD

    public boolean loadLastAnimation() {
        return saveAnimation;
    }

    public void setSaveAnimation(boolean saveAnimation) {
        this.saveAnimation = saveAnimation;
    }
=======
>>>>>>> origin/ColourPalette
}
