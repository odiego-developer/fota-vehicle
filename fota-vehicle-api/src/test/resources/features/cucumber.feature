Feature: This is the E-2-E test of project


  @Scenario:
  Scenario: The installable API should return only the installable hardware codes related to the VIN provided
    Given the following hardware and vin codes were persisted in data base
      | vinCode           | hardwareCode |
      | 3C3CFFER4ET929645 | 6VO6Uq       |
      | 3C3CFFER4ET929645 | ZCLFOe       |
      | 3C3CFFER4ET929645 | jyP5PK       |
      | WAUHFBFL1DN549274 | rlTcbX       |
    When I get the installable features by vin code 3C3CFFER4ET929645
    Then response status code is 200
    And the response body should only contains the following hardware codes
      | hardwareCode |
      | 6VO6Uq       |
      | ZCLFOe       |
      | jyP5PK       |