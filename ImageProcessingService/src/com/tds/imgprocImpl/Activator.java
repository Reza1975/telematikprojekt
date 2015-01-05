package com.tds.imgprocImpl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

import com.tds.camera.ICameraService;
import com.tds.imgproc.IImageProcessingService;

/**
 *
 * <b>ImageProcessingService <br />
 * com.tds.imgproc <br />
 * Activator <br />
 * </b>
 *
 * Description.
 *
 * @author Phillip Kopprasch<phillip.kopprasch@gmail.com>
 * @created 12.11.2014 21:13:29
 *
 */
public class Activator implements BundleActivator {

    private static BundleContext context;

    private IImageProcessingService service;
    
    private ICameraService camService;

    static BundleContext getContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;
        service = new ImageProcessingService();

        Dictionary<String, Object> params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, IImageProcessingService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides image processing capabilities.");
        context.registerService(IImageProcessingService.class.getName(), service, params);
        
        
        camService = new CameraService(context, 2);

        params = new Hashtable<>();
        params.put(Constants.SERVICE_PID, ICameraService.class.getName());
        params.put(Constants.SERVICE_DESCRIPTION, "Provides access to the camera interfaces.");
        params.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
        context.registerService(ICameraService.class.getName(), camService, params);

        camService.startCameraEvents(0, "obu/camera/driver", 15);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
    }

}
