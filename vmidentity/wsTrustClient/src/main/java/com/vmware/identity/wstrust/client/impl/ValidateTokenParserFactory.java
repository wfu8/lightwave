/*
 *  Copyright (c) 2012-2015 VMware, Inc.  All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *   use this file except in compliance with the License.  You may obtain a copy
 *   of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS, without
 *   warranties or conditions of any kind, EITHER EXPRESS OR IMPLIED.  See the
 *   License for the specific language governing permissions and limitations
 *   under the License.
 */
package com.vmware.identity.wstrust.client.impl;

import com.vmware.identity.wstrust.client.Credential;
import com.vmware.identity.wstrust.client.SecurityTokenServiceConfig;
import com.vmware.identity.wstrust.client.TokenCredential;
import com.vmware.identity.wstrust.client.TokenSpec;

/**
 * This class represents the factory implementation for creating the
 * {@link RequestParametersValidator}, {@link RequestBuilder},
 * {@link ResponseHandler} and {@link WsSecuritySignature} for validate token.
 *
 * @param <Boolean>
 *            Type of the response from the Security Token Service
 */
class ValidateTokenParserFactory extends RequestParserAbstractFactory<Boolean> {

    public ValidateTokenParserFactory(SecurityTokenServiceConfig config) {
        super(config);
    }

    @Override
    public RequestParametersValidator createRequestParametersValidator() {
        return new ValidateTokenParametersValidator();
    }

    @Override
    public RequestBuilder createRequestBuilder(Credential clientCredential, TokenSpec tokenSpec) {

        return new ValidateTokenRequestBuilder(((TokenCredential) clientCredential).getToken(), jaxbContext,
                stsConfig.getRequestValidityInSeconds());
    }

    @Override
    public ResponseHandler<Boolean> createResponseHandler() {
        return new ValidateTokenReponseHandler(jaxbContext);
    }

    @Override
    public WsSecuritySignature createWsSecuritySignature(Credential clientCredential, TokenSpec tokenSpec) {

        return WsSecuritySignatureFactory.createWsEmptySecuritySignature();
    }
}
