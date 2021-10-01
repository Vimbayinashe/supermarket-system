package com.company;

import java.math.BigDecimal;

public record Product(String name, String brand, Category category, BigDecimal price) {}

//NB: use BigDecimal(String) to avoid loss of accuracy similar to using floats & doubles
