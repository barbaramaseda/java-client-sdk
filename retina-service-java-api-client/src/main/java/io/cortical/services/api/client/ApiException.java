/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services.api.client;
/** Generated. **/
public class ApiException extends Exception {
  int code = 0;
  String message = null;
  /** Generated. **/
  public ApiException() {}

  public ApiException(int code, String message) {
    this.code = code;
    this.message = message;
  }
  /** Generated. **/
  public int getCode() {
    return code;
  }
  /** Generated. **/
  public void setCode(int code) {
    this.code = code;
  }
  /** Generated. **/
  public String getMessage() {
    return message;
  }
  /** Generated. **/
  public void setMessage(String message) {
    this.message = message;
  }
}

