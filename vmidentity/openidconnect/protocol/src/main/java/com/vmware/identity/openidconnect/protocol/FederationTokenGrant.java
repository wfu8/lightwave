/*
 *  Copyright (c) 2018 VMware, Inc.  All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License.  You may obtain a copy
 *  of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, without
 *  warranties or conditions of any kind, EITHER EXPRESS OR IMPLIED.  See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 */

package com.vmware.identity.openidconnect.protocol;

import com.vmware.identity.openidconnect.common.GrantType;
import com.vmware.identity.openidconnect.common.ParseException;
import com.vmware.identity.openidconnect.common.TokenClass;

import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

public class FederationTokenGrant extends AuthorizationGrant {

  private static final GrantType GRANT_TYPE = GrantType.FEDERATION_TOKEN;

  private final FederationToken federationToken;

  public FederationTokenGrant(FederationToken federationToken) {
    super(GRANT_TYPE);

    Validate.notNull(federationToken, "federationToken");
    this.federationToken = federationToken;
  }

  public FederationToken getFederationToken() {
    return this.federationToken;
  }

  @Override
  public Map<String, String> toParameters() {
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("grant_type", GRANT_TYPE.getValue());
    parameters.put("federation_token", this.federationToken.serialize());
    return parameters;
  }

  public static FederationTokenGrant parse(Map<String, String> parameters) throws ParseException {
    Validate.notNull(parameters, "parameters");

    GrantType grantType = GrantType.parse(ParameterMapUtils.getString(parameters, "grant_type"));
    if (grantType != GRANT_TYPE) {
      throw new ParseException("unexpected grant_type: " + grantType.getValue());
    }

    FederationToken federationToken = FederationToken.parse(parameters, TokenClass.ACCESS_TOKEN, FederationIDPIssuerType.CSP);

    return new FederationTokenGrant(federationToken);
  }
}
