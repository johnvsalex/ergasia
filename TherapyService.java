package com.example.domain;

public class TherapyService {
    private final String code;
    private final String name;
    private final String description;
    private final float cost;

    public TherapyService(String code, String name, String description, float cost) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
	
	public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getCost() {
        return cost;
    }

    @Override
    public int hashCode() {
        return code.hashCode() + 53;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        TherapyServiceImp therapyService = (TherapyServiceImp) obj;
        if(this.code != therapyService.code){
            return false;
        }
        return true;
    }


}
