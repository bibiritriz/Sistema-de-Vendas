/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatec.bancodedados.model;

import java.time.LocalDateTime;

/**
 *
 * @author isaqu
 */

public class OtpData {
    private final String otp;
    private final LocalDateTime creationTime;

    public OtpData(String otp, LocalDateTime creationTime) {
        this.otp = otp;
        this.creationTime = creationTime;
    }

    public String getOtp() { 
        return otp; 
    }

    public LocalDateTime getCreationTime() { 
        return creationTime; 
    }
}
