package io.yard.connector.config

import scala.concurrent.Future
import scala.collection.JavaConverters._

import com.typesafe.config.{ConfigValue, ConfigObject, ConfigRenderOptions}

import play.api.Play
import play.api.Configuration
import play.api.libs.json.{Json, JsValue, Reads, Writes}

import io.yard.common.models._

object ConfigConnector extends io.yard.connector.api.Connector {
  def orgs(implicit reader: Reads[Organization]) =
    Play.current.configuration
      .getObjectList("yardio.organizations")
      .map(asScalaSeq(_))
      .getOrElse(Seq.empty)
      .map(configValueToJson _)
      .map(reader.reads _)
      .map(_.asOpt)
      .collect {
        case Some(o) => o
      }

  def read[T <: ProviderConfiguration](provider: Provider, organization: Option[Organization])(implicit reader: Reads[T]): Future[Option[T]] =
    Future.successful(getJson(providerKey(provider, organization)).flatMap(reader.reads(_).asOpt))

  def save[T <: ProviderConfiguration](provider: Provider, organization: Option[Organization])(implicit writer: Writes[T]): Future[Boolean] = Future.successful(true)

  def read[T <: ModuleConfiguration](module: Module, organization: Option[Organization])(implicit reader: Reads[T]): Future[Option[T]] =
    Future.successful(getJson(moduleKey(module, organization)).flatMap(reader.reads(_).asOpt))

  def save[T <: ModuleConfiguration](module: Module, organization: Option[Organization])(implicit writer: Writes[T]): Future[Boolean] = Future.successful(true)

  private def providerKey(provider: Provider, organization: Option[Organization]): String =
    "yardio.provider." + provider.name + organization.map(".organization." + _.name).getOrElse("")

  private def moduleKey(module: Module, organization: Option[Organization]): String =
    "yardio.module." + module.name + organization.map(".organization." + _.name).getOrElse("")

  private def getJson(key: String): Option[JsValue] =
    Play.current.configuration
    .getObject(key)
    .map(configValueToJson _)

  private def configValueToJson(cv: ConfigValue): JsValue =
    Json.parse(cv.render(ConfigRenderOptions.concise().setJson(true)))

  private def asScalaSeq[A](l: java.util.List[A]): Seq[A] = asScalaBufferConverter(l).asScala.toSeq
  private def asScalaList[A](l: java.util.List[A]): List[A] = asScalaBufferConverter(l).asScala.toList
}
