package com.vova_cons.ny2020_test.screens.mario.components;

import com.badlogic.ashley.core.Component;

public class GravityComponent implements Component {
    public float force = -(9.8f * 9.8f); // meter per sec^2
}
