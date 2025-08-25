{
  queryPlanner: {
    mongosPlannerVersion: 1,
    winningPlan: {
      stage: 'SHARD_MERGE',
      shards: [
        {
          shardName: 'shard2RS',
          connectionString: 'shard2RS/shard2a:27018,shard2b:27018,shard2c:27018',
          serverInfo: {
            host: '7459c4260f64',
            port: 27018,
            version: '7.0.23',
            gitVersion: '78d6d71385be23831b5971993af60bcafed785bc'
          },
          namespace: 'billing.users',
          indexFilterSet: false,
          parsedQuery: { isActive: { '$eq': true } },
          queryHash: '2D2572A0',
          planCacheKey: 'D5355FBD',
          optimizationTimeMillis: 0,
          maxIndexedOrSolutionsReached: false,
          maxIndexedAndSolutionsReached: false,
          maxScansToExplodeReached: false,
          winningPlan: {
            stage: 'SHARDING_FILTER',
            inputStage: {
              stage: 'COLLSCAN',
              filter: { isActive: { '$eq': true } },
              direction: 'forward'
            }
          },
          rejectedPlans: []
        },
        {
          shardName: 'shard1RS',
          connectionString: 'shard1RS/shard1a:27018,shard1b:27018,shard1c:27018',
          serverInfo: {
            host: 'f477da071441',
            port: 27018,
            version: '7.0.23',
            gitVersion: '78d6d71385be23831b5971993af60bcafed785bc'
          },
          namespace: 'billing.users',
          indexFilterSet: false,
          parsedQuery: { isActive: { '$eq': true } },
          queryHash: '2D2572A0',
          planCacheKey: 'D5355FBD',
          optimizationTimeMillis: 0,
          maxIndexedOrSolutionsReached: false,
          maxIndexedAndSolutionsReached: false,
          maxScansToExplodeReached: false,
          winningPlan: {
            stage: 'SHARDING_FILTER',
            inputStage: {
              stage: 'COLLSCAN',
              filter: { isActive: { '$eq': true } },
              direction: 'forward'
            }
          },
          rejectedPlans: []
        },
        {
          shardName: 'shard3RS',
          connectionString: 'shard3RS/shard3a:27018,shard3b:27018,shard3c:27018',
          serverInfo: {
            host: 'daca12ed6cf7',
            port: 27018,
            version: '7.0.23',
            gitVersion: '78d6d71385be23831b5971993af60bcafed785bc'
          },
          namespace: 'billing.users',
          indexFilterSet: false,
          parsedQuery: { isActive: { '$eq': true } },
          queryHash: '2D2572A0',
          planCacheKey: 'D5355FBD',
          optimizationTimeMillis: 0,
          maxIndexedOrSolutionsReached: false,
          maxIndexedAndSolutionsReached: false,
          maxScansToExplodeReached: false,
          winningPlan: {
            stage: 'SHARDING_FILTER',
            inputStage: {
              stage: 'COLLSCAN',
              filter: { isActive: { '$eq': true } },
              direction: 'forward'
            }
          },
          rejectedPlans: []
        }
      ]
    }
  },
  executionStats: {
    nReturned: 9,
    executionTimeMillis: 5,
    totalKeysExamined: 0,
    totalDocsExamined: 11,
    executionStages: {
      stage: 'SHARD_MERGE',
      nReturned: 9,
      executionTimeMillis: 5,
      totalKeysExamined: 0,
      totalDocsExamined: 11,
      totalChildMillis: Long('0'),
      shards: [
        {
          shardName: 'shard2RS',
          executionSuccess: true,
          nReturned: 1,
          executionTimeMillis: 0,
          totalKeysExamined: 0,
          totalDocsExamined: 2,
          executionStages: {
            stage: 'SHARDING_FILTER',
            nReturned: 1,
            executionTimeMillisEstimate: 0,
            works: 3,
            advanced: 1,
            needTime: 1,
            needYield: 0,
            saveState: 0,
            restoreState: 0,
            isEOF: 1,
            chunkSkips: 0,
            inputStage: {
              stage: 'COLLSCAN',
              filter: { isActive: { '$eq': true } },
              nReturned: 1,
              executionTimeMillisEstimate: 0,
              works: 3,
              advanced: 1,
              needTime: 1,
              needYield: 0,
              saveState: 0,
              restoreState: 0,
              isEOF: 1,
              direction: 'forward',
              docsExamined: 2
            }
          }
        },
        {
          shardName: 'shard1RS',
          executionSuccess: true,
          nReturned: 3,
          executionTimeMillis: 0,
          totalKeysExamined: 0,
          totalDocsExamined: 4,
          executionStages: {
            stage: 'SHARDING_FILTER',
            nReturned: 3,
            executionTimeMillisEstimate: 0,
            works: 5,
            advanced: 3,
            needTime: 1,
            needYield: 0,
            saveState: 0,
            restoreState: 0,
            isEOF: 1,
            chunkSkips: 0,
            inputStage: {
              stage: 'COLLSCAN',
              filter: { isActive: { '$eq': true } },
              nReturned: 3,
              executionTimeMillisEstimate: 0,
              works: 5,
              advanced: 3,
              needTime: 1,
              needYield: 0,
              saveState: 0,
              restoreState: 0,
              isEOF: 1,
              direction: 'forward',
              docsExamined: 4
            }
          }
        },
        {
          shardName: 'shard3RS',
          executionSuccess: true,
          nReturned: 5,
          executionTimeMillis: 0,
          totalKeysExamined: 0,
          totalDocsExamined: 5,
          executionStages: {
            stage: 'SHARDING_FILTER',
            nReturned: 5,
            executionTimeMillisEstimate: 0,
            works: 6,
            advanced: 5,
            needTime: 0,
            needYield: 0,
            saveState: 0,
            restoreState: 0,
            isEOF: 1,
            chunkSkips: 0,
            inputStage: {
              stage: 'COLLSCAN',
              filter: { isActive: { '$eq': true } },
              nReturned: 5,
              executionTimeMillisEstimate: 0,
              works: 6,
              advanced: 5,
              needTime: 0,
              needYield: 0,
              saveState: 0,
              restoreState: 0,
              isEOF: 1,
              direction: 'forward',
              docsExamined: 5
            }
          }
        }
      ]
    }
  },
  serverInfo: {
    host: 'ac758a9a1445',
    port: 27017,
    version: '7.0.23',
    gitVersion: '78d6d71385be23831b5971993af60bcafed785bc'
  },
  serverParameters: {
    internalQueryFacetBufferSizeBytes: 104857600,
    internalQueryFacetMaxOutputDocSizeBytes: 104857600,
    internalLookupStageIntermediateDocumentMaxSizeBytes: 104857600,
    internalDocumentSourceGroupMaxMemoryBytes: 104857600,
    internalQueryMaxBlockingSortMemoryUsageBytes: 104857600,
    internalQueryProhibitBlockingMergeOnMongoS: 0,
    internalQueryMaxAddToSetBytes: 104857600,
    internalDocumentSourceSetWindowFieldsMaxMemoryBytes: 104857600,
    internalQueryFrameworkControl: 'forceClassicEngine'
  },
  command: {
    find: 'users',
    filter: { isActive: true },
    lsid: { id: UUID('3ded11a1-92f3-48b8-90b3-7fec239151c1') },
    '$clusterTime': {
      clusterTime: Timestamp({ t: 1756069494, i: 3 }),
      signature: {
        hash: Binary.createFromBase64('mrcOV6fgLNYksPgUACG3DP+syCE=', 0),
        keyId: Long('7542252821370896406')
      }
    },
    '$db': 'billing'
  },
  ok: 1,
  '$clusterTime': {
    clusterTime: Timestamp({ t: 1756069500, i: 1 }),
    signature: {
      hash: Binary.createFromBase64('c7vUROaD3pdsaKZ0/MAY5dBlHpM=', 0),
      keyId: Long('7542252821370896406')
    }
  },
  operationTime: Timestamp({ t: 1756069500, i: 1 })
}
