package com.coggroach.titan.graphics.renderer;

/**
 * Created by TARDIS on 13/11/2014.
 */
public interface IShaderProvider
{
    public String getVertexShader();
    public String getFragmentShader();
    public String getVertexShader(int resId);
    public String getFragmentShader(int resId);
}
