package doraieun.testapp4;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InvalidObjectException;

/**
 * Created by sungeun2.choi on 2015-12-22.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    String TAG = "CAMERA";
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera){
        super(context);
        mCamera = camera;

        mHolder = getHolder();
        mHolder.addCallback(this);

        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                parameters.set("orientation", "landscape");
                mCamera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }

            mCamera.setParameters(parameters);

            mCamera.setPreviewDisplay(mHolder);
        }catch (IOException e){
            System.out.println("##################### IOExeption " + e);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mCamera != null){
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height){
        Camera.Size result = null;
        Camera.Parameters p = mCamera.getParameters();
        for(int i = 0; i < p.getSupportedPreviewSizes().size(); i++){
            Camera.Size size = p.getSupportedPreviewSizes().get(i);
            if(size.width <= width && size.height <= height){
                if(result == null){
                    result = size;
                }else{
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if(newArea > resultArea){
                        result = size;
                    }

                }
            }
        }

        return result;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){
        if(mHolder.getSurface() == null){
            return;
        }
        mCamera.stopPreview();

        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = getBestPreviewSize(w, h);
        parameters.setPreviewSize(size.width, size.height);
        mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }catch(IOException e){
            System.out.println("######################### IOException " + e);
        }
    }


}
