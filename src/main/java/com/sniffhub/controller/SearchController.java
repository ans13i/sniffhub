package com.sniffhub.controller;

import com.sniffhub.model.Dog;
import com.sniffhub.model.DogManagementModel;
import com.sniffhub.view.Main;

import java.util.ArrayList;

public class SearchController {
    private final DogManagementModel model;

    public SearchController(DogManagementModel model) {
        this.model = model;

    }

    public ArrayList<Dog> searchByOwner(String ownerName) {
        return model.filterDogsByOwner(ownerName);
    }


    public void deleteDog(Dog dog) {

    } // 추후에 생성
}
