[
  {
    _id: ObjectId('66c7e0000000000000000003'),
    entityType: 'invoice',
    entityId: ObjectId('66c7c0000000000000000001'),
    action: 'status_change',
    timestamp: ISODate('2023-12-01T09:00:00.000Z'),
    createdAt: ISODate('2023-12-01T09:00:00.000Z'),
    updatedAt: ISODate('2023-12-01T09:00:00.000Z'),
    details: { performedBy: 'user', changes: { status: 'overdue' } }
  },
  {
    _id: ObjectId('66c7e0000000000000000004'),
    entityType: 'subscription',
    entityId: ObjectId('66c7d0000000000000000001'),
    action: 'cancelled',
    timestamp: ISODate('2024-10-15T00:00:00.000Z'),
    createdAt: ISODate('2024-10-15T00:00:00.000Z'),
    updatedAt: ISODate('2024-10-15T00:00:00.000Z'),
    details: { performedBy: 'user', changes: { status: 'cancelled' } }
  },
  {
    _id: ObjectId('66c7e000000000000000000d'),
    entityType: 'invoice',
    entityId: ObjectId('66c7c000000000000000000d'),
    action: 'status_change',
    timestamp: ISODate('2024-07-16T16:35:00.000Z'),
    createdAt: ISODate('2024-07-16T16:35:00.000Z'),
    updatedAt: ISODate('2024-07-16T16:35:00.000Z'),
    details: { performedBy: 'system', changes: { status: 'overdue' } }
  },
  {
    _id: ObjectId('66c7e000000000000000000f'),
    entityType: 'user',
    entityId: ObjectId('66c7a000000000000000000f'),
    action: 'balance_update',
    timestamp: ISODate('2024-07-30T10:55:00.000Z'),
    createdAt: ISODate('2024-07-30T10:55:00.000Z'),
    updatedAt: ISODate('2024-07-30T10:55:00.000Z'),
    details: { performedBy: 'system', changes: { balance: 175.6 } }
  },
  {
    _id: ObjectId('66c7e0000000000000000005'),
    entityType: 'user',
    entityId: ObjectId('66c7a0000000000000000005'),
    action: 'deactivated',
    timestamp: ISODate('2025-06-05T15:40:00.000Z'),
    createdAt: ISODate('2025-06-05T15:40:00.000Z'),
    updatedAt: ISODate('2025-06-05T15:40:00.000Z'),
    details: { performedBy: 'admin', changes: { isActive: false } }
  },
  {
    _id: ObjectId('66c7e0000000000000000009'),
    entityType: 'user',
    entityId: ObjectId('66c7a0000000000000000009'),
    action: 'profile_update',
    timestamp: ISODate('2024-08-01T22:15:00.000Z'),
    createdAt: ISODate('2024-08-01T22:15:00.000Z'),
    updatedAt: ISODate('2024-08-01T22:15:00.000Z'),
    details: {
      performedBy: 'user',
      changes: { hobbies: [ 'swimming', 'movies' ] }
    }
  },
  {
    _id: ObjectId('66c7e000000000000000000e'),
    entityType: 'subscription',
    entityId: ObjectId('66c7d000000000000000000e'),
    action: 'cancelled',
    timestamp: ISODate('2024-04-10T00:00:00.000Z'),
    createdAt: ISODate('2024-04-10T00:00:00.000Z'),
    updatedAt: ISODate('2024-04-10T00:00:00.000Z'),
    details: { performedBy: 'user', changes: { status: 'cancelled' } }
  },
  {
    _id: ObjectId('66c7e0000000000000000008'),
    entityType: 'subscription',
    entityId: ObjectId('66c7d0000000000000000008'),
    action: 'renewed',
    timestamp: ISODate('2024-03-10T00:00:00.000Z'),
    createdAt: ISODate('2024-03-10T00:00:00.000Z'),
    updatedAt: ISODate('2024-03-10T00:00:00.000Z'),
    details: { performedBy: 'user', changes: { status: 'inactive' } }
  },
  {
    _id: ObjectId('66c7e000000000000000000a'),
    entityType: 'transaction',
    entityId: ObjectId('66c7b000000000000000000a'),
    action: 'failed',
    timestamp: ISODate('2025-03-12T08:15:00.000Z'),
    createdAt: ISODate('2025-03-12T08:15:00.000Z'),
    updatedAt: ISODate('2025-03-12T08:15:00.000Z'),
    details: { performedBy: 'system', changes: { status: 'completed' } }
  },
  {
    _id: ObjectId('66c7e000000000000000000c'),
    entityType: 'transaction',
    entityId: ObjectId('66c7b000000000000000000c'),
    action: 'failed',
    timestamp: ISODate('2024-07-20T09:35:00.000Z'),
    createdAt: ISODate('2024-07-20T09:35:00.000Z'),
    updatedAt: ISODate('2024-07-20T09:35:00.000Z'),
    details: { performedBy: 'system', changes: { status: 'failed' } }
  }
]
