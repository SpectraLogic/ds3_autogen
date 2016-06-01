class HeadBucketStatus(object):
  """Head bucket return values"""
  EXISTS = 'EXISTS'  # 200
  NOTAUTHORIZED = 'NOTAUTHORIZED' # 403
  DOESNTEXIST = 'DOESNTEXIST' # 404
  UNKNOWN = 'UNKNOWN'

class HeadObjectStatus(object):
  """Head object return values"""
  EXISTS = 'EXISTS'  # 200
  DOESNTEXIST = 'DOESNTEXIST' # 404
  UNKNOWN = 'UNKNOWN'

