import axios from "axios";

export const handler = async (event) => {
    console.log("Verify Auth Challenge Trigger Event:", JSON.stringify(event));

    const { privateChallengeParameters, challengeAnswer } = event.request;

    try {
        // Verify Google ID Token matches the challenge answer
        const googleTokenInfoResponse = await axios.get(`https://oauth2.googleapis.com/tokeninfo`, {
            params: { id_token: challengeAnswer },
        });
        const googlePayload = googleTokenInfoResponse.data;

        if (googlePayload.error || googlePayload.sub !== privateChallengeParameters.googleIdToken) {
            event.response.answerCorrect = false; // Verification failed
        } else {
            event.response.answerCorrect = true; // Verification succeeded
        }
    } catch (error) {
        console.error("Error verifying Google ID Token:", error);
        event.response.answerCorrect = false;
    }

    return event;
};
