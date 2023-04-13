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
public enum BookingStatusEnum {
    PENDING{
         @Override
        public String toString() {
            return "Pending";
        }
    }, CONFIRMED{
         @Override
        public String toString() {
            return "Confirmed";
        }
    }, COMPLETED{
         @Override
        public String toString() {
            return "Completed";
        }
    }, CANCELLED{
         @Override
            public String toString() {
                return "Cancelled";
            }
    }, REJECTED{
         @Override
            public String toString() {
                return "Rejected";
            }
    }, TORATE {
         @Override
        public String toString() {
            return "To Rate";
        }
    }
            
}
