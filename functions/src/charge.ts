import * as functions from "firebase-functions";

export const chargeApi = async (
  req: functions.https.Request,
  res: functions.Response
) => {
  // TODO: 3. リクエストからトークンと金額を取得
  // TODO: 4. トークンから売上を作成
  try {
    const { email, amount } = req.body;
    res.status(200).send(JSON.stringify({ email, amount }));
  } catch (e) {
    console.warn(e, "error in creating charge");
    res.status(400).send("charge error");
  }
};
