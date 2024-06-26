package com.carlease.project.car;

import com.carlease.project.enums.UserRole;
import com.carlease.project.exceptions.CarNotFoundException;
import com.carlease.project.exceptions.UserException;
import com.carlease.project.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cars")
public class CarController {
    private CarServiceImpl carServiceImpl;

    @Autowired
    private CarController(CarServiceImpl carServiceImpl) {
        this.carServiceImpl = carServiceImpl;
    }

    @GetMapping(produces = "application/json")
    ResponseEntity<List<CarDto>> getCars() {
        List<CarDto> list = carServiceImpl.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<CarDto> getCar(@PathVariable("id") long id) throws CarNotFoundException {

        CarDto car = carServiceImpl.findById(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping("/{make}/models")
    @ResponseBody
    ResponseEntity<List<String>> getModels(@PathVariable("make") String make) {
        List<String> models = carServiceImpl.findModels(make);
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @PatchMapping("/")
    public ResponseEntity<CarDto> updateCarPrices(@RequestBody CarDto carDto, @RequestHeader("userId") long userId, @RequestHeader("role") UserRole role) {
        try {
            CarDto updatedCar = carServiceImpl.updatePrice(carDto, userId, role);
            return ResponseEntity.ok(updatedCar);
        } catch (CarNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UserException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}