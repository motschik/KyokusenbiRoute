cd $APP_PATH

screen -ls KyokusenbiRoute
if [ $? -eq 0 ]; then
  screen -S KyokusenbiRoute -X quit
fi

ls KyokusenbiRoute-*.jar
if [ $? -eq 0 ]; then
  rm KyokusenbiRoute-*.jar
fi
