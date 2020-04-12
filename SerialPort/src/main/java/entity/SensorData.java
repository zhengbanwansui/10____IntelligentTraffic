package entity;

import java.io.Serializable;

public class SensorData implements Serializable {

    private Integer id;

    private Integer temperature;

    private Integer humidness;

    private Integer pressure;

    private Integer light;

    private Integer distance;

    public SensorData(Integer id, Integer temperature, Integer humidness, Integer pressure, Integer light, Integer distance) {
        this.id = id;
        this.temperature = temperature;
        this.humidness = humidness;
        this.pressure = pressure;
        this.light = light;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public Integer getHumidness() {
        return humidness;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getLight() {
        return light;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public void setHumidness(Integer humidness) {
        this.humidness = humidness;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public void setLight(Integer light) {
        this.light = light;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "temperature=" + temperature +
                ", humidness=" + humidness +
                ", pressure=" + pressure +
                ", light=" + light +
                ", distance=" + distance +
                '}';
    }
}
