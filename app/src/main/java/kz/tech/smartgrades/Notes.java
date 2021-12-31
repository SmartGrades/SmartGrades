/*


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = getLayoutInflater().inflate(R.layout.ad_school_add_new_column, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        EditText etColumnName = view.findViewById(R.id.etColumnName);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvAdd = view.findViewById(R.id.tvAdd);

        etColumnName.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }
            @Override
            public void afterTextChanged(Editable arg0){
                if(arg0.length() == 0){
                    tvAdd.setEnabled(false);
                    tvAdd.setTextColor(getResources().getColor(R.color.gray_default));
                    tvAdd.setBackground(null);
                    tvAdd.setPadding(10, 10, 10, 10);
                }
                else{
                    tvAdd.setEnabled(true);
                    tvAdd.setTextColor(getResources().getColor(R.color.white));
                    tvAdd.setBackgroundResource(R.drawable.background_square_blue_sea);
                    tvAdd.setPadding(10, 10, 10, 10);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();

                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("SchoolId", SCHOOL_ID);
                jsonData.addProperty("Name", etColumnName.getText().toString());

                String SOAP = SoapRequest(func_SchoolAddColumnToClassess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback(){
                    @Override
                    public void onFailure(final Call call, IOException e){
                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException{
                        if(response.isSuccessful()){
                            String result = _Web.XMLReader(response.body().string());
                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                    activity.alert.onToast(answer.getMessage());
                                    if(answer.isSuccess()) activity.presenter.onStartPresenter();
                                }
                            });
                        }
                    }
                });
            }
        });


    ПАРСИНГ МАССИВА JSON
ArrayList<ModelDefaultChat> modelChats = new Gson().fromJson(result, new TypeToken<ArrayList<ModelDefaultChat>>(){
                                }.getType());



  ПРОВЕРКА НА ВИДИМОСТЬ КЛАВИАТУРЫ





JsonObject jsonData = new JsonObject();
        jsonData.addProperty("Login", Login);
        jsonData.addProperty("Password", Password);
        jsonData.addProperty("Token", Token);

        String SOAP = SoapRequest(func_AuthUser, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
@Override
public void onFailure(final Call call, IOException e) {
        runOnUiThread(() -> {
        alert.onToast("Сервер не доступен");
//                    String answer = e.toString();
//                    alert.onToast(answer);
        });
        }
@Override
public void onResponse(Call call, final Response response) throws IOException {
        if (response.isSuccessful()) {
        String result = XMLToJson(response.body().string());
        ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
        runOnUiThread(() -> {
        if (answer.isSuccess()) {
        login.saveUserData(new Gson().fromJson(result, ModelUser.class));
        onNextActivity(login.loadUserDate(LoginKey.TYPE));
        }
        else {
        if (isAutoAuth) {
        login.deleteUserDate();
        onStartAuth();
        isAutoAuth = false;
        }
        else {
        if (answer.getCode() == 100){
        alert.onToast("Не верно указан номер телефона или почта");
        }
        else if (answer.getCode() == 110)
        alert.onToast("Не указан номер телефона или почта");
        else if (answer.getCode() == 200)
        alert.onToast("Не верно указан пароль");
        else if (answer.getCode() == 210) alert.onToast("Не указан пароль");
        else if (answer.getCode() == 10)
        alert.onToast("Пользователь не найден");
        }
        }
        });
        }
        }
        });




 */