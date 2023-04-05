/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Amirul
 */
public enum ServiceTypeEnum {
    PHOTOGRAPHY{
        @Override
        public String toString() {
            return "Photography";
        }
    }, VIDEOGRAPHY{
         @Override
            public String toString() {
                return "Videography";
            }
    }, PHOTO_EDITING{
     @Override
            public String toString() {
                return "Photo Editing";
            }
    }, VIDEO_EDITING{
         @Override
            public String toString() {
                return "Video Editing";
            }
    }, EQUIPMENT_RENTAL{
         @Override
            public String toString() {
                return "Equipment Rental";
            }
    }
    
}
