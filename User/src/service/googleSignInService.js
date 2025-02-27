import { CognitoIdentityProviderClient, InitiateAuthCommand, RespondToAuthChallengeCommand } from "@aws-sdk/client-cognito-identity-provider";

export default class GoogleCognitoSignInService {
    constructor(region, clientId) {
        this.cognitoClient = new CognitoIdentityProviderClient({ region });
        this.clientId = clientId;
    }

    async handleGoogleLogin(email, idToken) {
        const authParams = {
            AuthFlow: "CUSTOM_AUTH",
            ClientId: this.clientId,
            AuthParameters: {
                USERNAME: email,
            },
        };

        const authResponse = await this.cognitoClient.send(new InitiateAuthCommand(authParams));

        if (!authResponse.Session || !authResponse.ChallengeName) {
            throw new Error("Session is required to continue authentication.");
        }

        const respondParams = {
            ChallengeName: authResponse.ChallengeName,
            ClientId: this.clientId,
            ChallengeResponses: {
                USERNAME: email,
                ANSWER: idToken,
            },
            Session: authResponse.Session,
        };

        const respondResponse = await this.cognitoClient.send(new RespondToAuthChallengeCommand(respondParams));

        if (!respondResponse.AuthenticationResult) {
            throw new Error("Authentication failed. Unable to retrieve tokens.");
        }

        return {
            accessToken: respondResponse.AuthenticationResult.AccessToken,
            idToken: respondResponse.AuthenticationResult.IdToken,
            refreshToken: respondResponse.AuthenticationResult.RefreshToken,
        };
    }
}
