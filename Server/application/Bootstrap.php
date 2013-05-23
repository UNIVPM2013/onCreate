<?php

class Bootstrap extends Zend_Application_Bootstrap_Bootstrap {

    protected $_logger;

    protected function _initLogging() {
        $logger = new Zend_Log();
        $writer = new Zend_Log_Writer_Firebug();
        $logger->addWriter($writer);
        $this->_logger = $logger;
        Zend_Registry::set('log', $logger);
    }

    protected function _initRoutes() {
        $front = Zend_Controller_Front::getInstance();
        $router = $front -> getRouter();
        $restRoute = new Zend_Rest_Route($front);
        $router -> addRoute('default', $restRoute);
    }

}
