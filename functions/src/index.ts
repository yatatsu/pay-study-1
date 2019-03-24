import * as functions from "firebase-functions";
import { chargeApi } from "./charge";

export const charge = functions.https.onRequest(chargeApi);
