package io.yard.connector.config

import io.yard.module.core.Api

class ConfigConnectorConfig(application: play.api.Application) extends play.api.Plugin {
  override def onStart = {
    Api.registerConnector(ConfigConnector)
  }
}
