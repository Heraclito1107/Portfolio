/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

import java.awt.Color;

/**
 *
 * @author Heraclito
 * @version 2.7
 */
public class Material {
    private Color color;
    private Boolean refract;
    private Boolean reflect;
    private Boolean transparent;
    private Boolean specular;
    private Boolean smoothing;
    private float refractionAmount;
    private float reflectionAmount;
    private float transparencyAmount;
    private float specularConst;
    public Material(Color color, 
                    Boolean refract, 
                    Boolean reflect, 
                    Boolean transparent, 
                    Boolean specular, 
                    Boolean smoothing) {
        this.color = color;
        this.refract = refract;
        this.reflect = reflect;
        this.transparent = transparent;
        this.specular = specular;
        this.smoothing = smoothing;
        if(refract)
            refractionAmount = 1.6f;
        if(reflect)
            reflectionAmount = 0.5f;
        if(transparent)
            transparencyAmount = 0.0f;
        if(specular)
            specularConst = 100f;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean refracts() {
        return refract;
    }

    public void setRefract(Boolean refract) {
        this.refract = refract;
    }

    public Boolean reflects() {
        return reflect;
    }

    public void setReflect(Boolean reflect) {
        this.reflect = reflect;
    }

    public Boolean getSmoothing() {
        return smoothing;
    }

    public void setSmoothing(Boolean smoothing) {
        this.smoothing = smoothing;
    }

    public float getRefractionAmount() {
        return refractionAmount;
    }

    public void setRefractionAmount(float refractionAmount) {
        this.refractionAmount = refractionAmount;
    }

    public float getReflectionAmount() {
        return reflectionAmount;
    }

    public void setReflectionAmount(float reflectionAmount) {
        this.reflectionAmount = reflectionAmount;
    }

    public float getTransparencyAmount() {
        return transparencyAmount;
    }

    public void setTransparencyAmount(float transparencyAmount) {
        this.transparencyAmount = transparencyAmount;
    }

    public Boolean isTransparent() {
        return transparent;
    }
    
    public Boolean isSpecular(){
        return specular;
    }
    
    public float getSpecularConst() {
        return specularConst;
    }

    public void setSpecularConst(float specularConst) {
        this.specularConst = specularConst;
    }
    
    public static Color addColors(Color colorA, Color colorB)
    {
        return new Color(MathGenerals.clamp(colorA.getRed() + colorB.getRed(), 0, 255), 
                         MathGenerals.clamp(colorA.getGreen() + colorB.getGreen(), 0, 255), 
                         MathGenerals.clamp(colorA.getBlue() + colorB.getBlue(), 0, 255));
    }
    
    public static Color scalarColorProduct(float scalar, Color color)
    {
        
        return new Color(MathGenerals.clamp((int)(scalar*color.getRed()),0, 255), 
                         MathGenerals.clamp((int)(scalar*color.getGreen()),0, 255), 
                         MathGenerals.clamp((int)(scalar*color.getBlue()),0, 255));
    }
}
