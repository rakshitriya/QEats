
/*
 *
 *  * Copyright (c) Crio.Do 2019. All rights reserved
 *
 */

package com.crio.qeats.services;

import com.crio.qeats.dto.Restaurant;
import com.crio.qeats.exchanges.GetRestaurantsRequest;
import com.crio.qeats.exchanges.GetRestaurantsResponse;
import com.crio.qeats.repositoryservices.RestaurantRepositoryService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@Log4j2
public class RestaurantServiceImpl implements RestaurantService {

  private final Double peakHoursServingRadiusInKms = 3.0;
  private final Double normalHoursServingRadiusInKms = 5.0;

  @Autowired
  public RestaurantRepositoryService restaurantRepositoryService;


  // TODO: CRIO_TASK_MODULE_RESTAURANTSAPI - Implement findAllRestaurantsCloseby.
  // Check RestaurantService.java file for the interface contract.
  @Override
  public GetRestaurantsResponse findAllRestaurantsCloseBy(
      GetRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) {

    List<Restaurant> restaurants ;
     Double latitude = getRestaurantsRequest.getLatitude();
     Double longitude = getRestaurantsRequest.getLongitude();
     int h = currentTime.getHour();
     int m = currentTime.getMinute();
     if ((h >= 8 && h <= 9) || (h == 10 && m == 0) || (h >= 13 && h <= 14) || (h >= 19 && h <= 20) || (h == 21 && m == 0))

     {
      restaurants=  restaurantRepositoryService.findAllRestaurantsCloseBy(latitude, longitude, currentTime, peakHoursServingRadiusInKms);
     }
    else{
      restaurants= restaurantRepositoryService.findAllRestaurantsCloseBy(latitude, longitude, currentTime, normalHoursServingRadiusInKms);
    }
      GetRestaurantsResponse response = new GetRestaurantsResponse(restaurants);
      log.info(response);
      return response ;
    
  }


}

