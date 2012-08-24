package org.reactome.summary.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.kiouri.sliderbar.client.view.SliderBarHorizontal;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Slider Bar Interface Element implemented using gwt-slider-bar plugin 
 * http://code.google.com/p/gwt-slider-bar/
 * @author maulik
 *
 */
public class LimitSliderBar extends SliderBarHorizontal {
        
        ImagesKDEHorizontalLeftBW images = GWT.create(ImagesKDEHorizontalLeftBW.class);
        
        public LimitSliderBar(int maxValue, String width) {
            setLessWidget(new Image(images.less()));
         //   setMoreWidget(new Image(images.more()));
            setScaleWidget(new Image(images.scale().getUrl()), 16);
            setMoreWidget(new Image(images.more()));
            setDragWidget(new Image(images.drag()));
            this.setWidth(width);
            this.setMaxValue(maxValue);         
        }
                
        interface ImagesKDEHorizontalLeftBW extends ClientBundle {
                
                @Source("drag.png")
                ImageResource drag();

                @Source("less.png")
                ImageResource less();

                @Source("more.png")
                ImageResource more();

                @Source("scale.png")
                DataResource scale();
        }       
        
}