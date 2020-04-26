package com.acnhcompanion.application;

import com.acnhcompanion.application.Bugs.Bug;
import com.acnhcompanion.application.Fish.Fish;
import com.acnhcompanion.application.Fossils.Fossil;

import java.util.ArrayList;
import java.util.List;

public class Search_Item {
    public int imgID;
    public String critterName;
    public String critterType;

    public Search_Item(){

    }

    public Search_Item(Fish fish){
        this.critterName = fish.name;
        this.critterType = "fish";
        this.imgID = fish.imgID;
    }

    public Search_Item(Bug bug){
        this.critterName = bug.name;
        this.critterType = "bug";
        this.imgID = bug.imgID;
    }

    public Search_Item(Fossil fossil){
        this.critterName = fossil.name;
        this.critterType = "fossil";
        this.imgID = fossil.imgID;
    }

    public List<Search_Item> Search_Item_FishList(List<Fish> fishes){
        List<Search_Item> toReturn = new ArrayList<>();
        for(Fish item : fishes){
            Search_Item newItem = new Search_Item(item);
            toReturn.add(newItem);
        }
        if(toReturn.size() != 0) {
            return toReturn;
        } else {
            return null;
        }
    }

    public List<Search_Item> Search_Item_BugList(List<Bug> bugs){
        List<Search_Item> toReturn = new ArrayList<>();
        for(Bug item : bugs){
            Search_Item newItem = new Search_Item(item);
            toReturn.add(newItem);
        }
        if(toReturn.size() != 0) {
            return toReturn;
        } else {
            return null;
        }
    }

    public List<Search_Item> Search_Item_FossilList(List<Fossil> fossils){
        List<Search_Item> toReturn = new ArrayList<>();
        for(Fossil item : fossils){
            Search_Item newItem = new Search_Item(item);
            toReturn.add(newItem);
        }
        if(toReturn.size() != 0) {
            return toReturn;
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }

        Search_Item temp = (Search_Item) obj;

        return (temp.critterName == critterName);
    }

    @Override
    public int hashCode(){
        return critterName.hashCode();
    }
}
