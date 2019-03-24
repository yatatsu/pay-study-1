import * as functions from "firebase-functions";

export const chargeApi = async (
  req: functions.https.Request,
  res: functions.Response
) => {
  // 3. リクエストからトークンと金額を取得
  const { token, amount } = req.body;
  try {
    // TODO: 4. トークンから売上を作成
    const payjp = require("payjp")("sk_test_e5117cce209cec113cf71b94");
    await payjp.charges.create({
      amount: amount,
      currency: "jpy",
      card: token,
      capture: true
    });
    res.status(200).send();
  } catch (e) {
    console.warn(e, "error in creating charge");
    res.status(400).send("charge error");
  }
};
