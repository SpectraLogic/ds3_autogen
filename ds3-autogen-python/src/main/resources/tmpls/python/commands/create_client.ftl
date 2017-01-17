def createClientFromEnv():
  """Build a Client from environment variables.

     Required: DS3_ACCESS_KEY, DS3_SECRET_KEY, DS3_ENDPOINT

     Optional: http_proxy
  """
  access_key = os.environ.get('DS3_ACCESS_KEY')
  secret_key = os.environ.get('DS3_SECRET_KEY')
  endpoint = os.environ.get('DS3_ENDPOINT')
  proxy = os.environ.get('http_proxy')

  if None in (access_key, secret_key, endpoint):
    raise Exception('Required environment variables are not set: DS3_ACCESS_KEY, DS3_SECRET_KEY, DS3_ENDPOINT')

  creds = Credentials(access_key, secret_key)
  client = Client(endpoint, creds, proxy)
  return client