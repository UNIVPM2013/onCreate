<?php
class CittaController extends Zend_Rest_Controller {

    public function init() {
        $this -> _helper -> viewRenderer -> setNoRender(true);
    }

    public function indexAction() {
        $data = array('message'=>'From indexAction() returning all articles');
        $jsonData = Zend_Json::encode($data);
        $this->getResponse()->setHeader('Content-type','application/json')
                            ->setBody($jsonData)
                            ->setHttpResponseCode(200);
    }

    public function getAction() {
        // get param from url
        $parm = $this->_getParam('param');
        
        // execute query
        $data = array('data' => 'query result');
        
        // send response object
        $jsonData = Zend_Json::encode($data);
        $this->getResponse()->setHeader('Content-type','application/json')
                            ->setBody($jsonData)         
                            ->setHttpResponseCode(200);
    }

    public function postAction() {
        // get param from url
        $parm = $this->_getParam('param');
        
        // execute query
        $data = array('data' => 'query result');
        
        // send response object
        $jsonData = Zend_Json::encode($data);
        $this->getResponse()->setHeader('Content-type','application/json')
                            ->setBody($jsonData)         
                            ->setHttpResponseCode(200);
    }

    public function putAction() {
        // get param from url
        $parm = $this->_getParam('param');
        
        // execute query
        $data = array('data' => 'query result');
        
        // send response object
        $jsonData = Zend_Json::encode($data);
        $this->getResponse()->setHeader('Content-type','application/json')
                            ->setBody($jsonData)         
                            ->setHttpResponseCode(200);
    }

    public function deleteAction() {
        // get param from url
        $parm = $this->_getParam('param');
        
        // execute query
        $data = array('data' => 'query result');
        
        // send response object
        $jsonData = Zend_Json::encode($data);
        $this->getResponse()->setHeader('Content-type','application/json')
                            ->setBody($jsonData)         
                            ->setHttpResponseCode(200);
    }
}
