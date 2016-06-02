class HeadRequestStatus(object):
  """Head bucket and head object return values"""
  EXISTS = 'EXISTS'  # 200
  NOTAUTHORIZED = 'NOTAUTHORIZED' # 403
  DOESNTEXIST = 'DOESNTEXIST' # 404
  UNKNOWN = 'UNKNOWN'
