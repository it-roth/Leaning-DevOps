```markdown
# ☁️ NOTE DevOps

A practical collection of **notes, configurations, manifests, diagrams, and labs** covering core DevOps, containerization, Kubernetes orchestration, cloud-native tooling, and observability.

---

## 🧭 Learning Pipeline

```text
Linux & Networking
        │
        ▼
Docker & Containers
        │
        ▼
Docker Compose
        │
        ▼
Kubernetes (Core & Internals)
   ├── Architecture & Internals (etcd, CoreDNS)
   ├── Workloads & Scheduling
   ├── Networking, Ingress & Security (RBAC, Policies)
   └── Storage (PV, PVC, StorageClass)
        │
        ▼
Cloud-Native Ecosystem
   ├── Package Management: Helm
   ├── Edge Routing & TLS: Traefik + cert-manager
   └── Service Mesh: Istio (Traffic, mTLS)
        │
        ▼
Observability & Management
   ├── Monitoring & Metrics: kube-prometheus-stack
   └── Visual Management: Headlamp
        │
        ▼
Cloud & Production Infrastructure (GCP)

```

---

## 🧰 Technology Matrix

| Domain | Tools / Technologies | Key Coverage |
| --- | --- | --- |
| **System & Web** | Ubuntu, Nginx, Nexus OSS | Linux admin, SSH, reverse proxy, artifact registry |
| **Containers** | Docker, Docker Compose | Multi-stage builds, networking, volumes, compose stacks |
| **Orchestration** | Kubernetes | Architecture, workloads, storage, RBAC, scheduling |
| **K8s Internals** | etcd, CoreDNS | Raft quorum, state store, DNS resolution, service discovery |
| **Packaging & Edge** | Helm, Traefik, cert-manager | Custom charts, IngressRoute, ACME, automated TLS |
| **Service Mesh** | Istio | Sidecar proxy (Envoy), traffic shifting, canary, mTLS |
| **Observability** | Prometheus, Grafana, Alertmanager | Metrics collection, ServiceMonitor, alerting rules, dashboards |
| **Dashboard** | Headlamp | Cluster inspection, workload state visualization |
| **Cloud** | Google Cloud Platform (GCP) | Compute Engine, VPC networks, firewall rules |

---

## ☸️ Kubernetes Deep Dive

### Control Plane & Node Topology

```text
               Kubernetes Cluster
 ┌──────────────────────────────────────────┐
 │              Control Plane               │
 │  kube-apiserver │ kube-scheduler         │
 │  kube-controller-manager │ etcd (Quorum) │
 └────────────────────┬─────────────────────┘
                      │
 ┌────────────────────▼─────────────────────┐
 │               Worker Nodes               │
 │  kubelet │ container runtime │ CNI       │
 │  ┌─────────────────┐ ┌─────────────────┐ │
 │  │ Pod (App Container)│ │ Pod (App Container)│ │
 │  └─────────────────┘ └─────────────────┘ │
 └──────────────────────────────────────────┘

```

### Module Breakdown

* **Workloads:** Pods, Deployments, ReplicaSets, StatefulSets, DaemonSets, Jobs, CronJobs
* **Networking & Discovery:** ClusterIP, NodePort, LoadBalancer, Ingress, CoreDNS resolution, NetworkPolicies
* **Storage Engine:** Volumes, PersistentVolumes (PV), PersistentVolumeClaims (PVC), StorageClasses, Dynamic Provisioning
* **Scheduling Mechanics:** NodeSelector, Node/Pod Affinity & Anti-Affinity, Taints, Tolerations, Resource Requests/Limits
* **Security & Access:** ServiceAccounts, RBAC (Role, ClusterRole, RoleBinding, ClusterRoleBinding), Secrets, ConfigMaps

---

## 🕸️ Cloud-Native & Observability Topology

### Ingress & Edge Routing

```text
Client Request ──► Traefik (Ingress Controller)
                         ├── app.domain.com   ──► Frontend Service
                         ├── api.domain.com   ──► Backend Service
                         └── cert-manager     ──► Let's Encrypt (Automated TLS)

```

### Service Mesh (Istio)

```text
Client ──► [Istio Ingress Gateway]
                 │
                 ▼
          [Envoy Sidecar] ──► Frontend Service
                 │ (mTLS / Traffic Split)
                 ▼
          [Envoy Sidecar] ──► Backend Service
                 │
                 ▼
          Database Pod

```

### Monitoring Pipeline (kube-prometheus-stack)

```text
Target Pods / Nodes ──► ServiceMonitor / Exporters ──► Prometheus ──► Grafana Dashboards
                                                           │
                                                           ▼
                                                      Alertmanager ──► Notifications

```

---

## 📂 Repository Layout

```text
.
├── 01-linux-infrastructure/
│   ├── ubuntu-admin/
│   ├── networking-basics/
│   └── nginx-reverse-proxy/
├── 02-containerization/
│   ├── docker-fundamentals/
│   ├── multi-stage-dockerfiles/
│   └── docker-compose-stacks/
├── 03-kubernetes-core/
│   ├── 01-architecture/
│   ├── 02-workloads/
│   ├── 03-networking/
│   ├── 04-storage/
│   ├── 05-scheduling/
│   └── 06-security-rbac/
├── 04-kubernetes-internals/
│   ├── etcd-cluster/
│   └── coredns-discovery/
├── 05-package-management-helm/
│   ├── chart-basics/
│   └── custom-charts/
├── 06-ingress-tls/
│   ├── traefik/
│   └── cert-manager/
├── 07-service-mesh-istio/
│   ├── traffic-shifting/
│   └── security-mtls/
├── 08-observability/
│   └── kube-prometheus-stack/
├── 09-cluster-ui/
│   └── headlamp/
├── 10-cloud-gcp/
│   └── gce-networking/
└── manifests/

```

---

## ⚡ Core Command Cheat Sheet

### Kubernetes Administration

```bash
# Cluster & Node Status
kubectl get nodes -o wide
kubectl cluster-info

# Resource Inspection Across Namespaces
kubectl get pods,svc,ingress -A
kubectl get pv,pvc -A
kubectl get networkpolicy,sa,roles,rolebindings -A

# Debugging & Logs
kubectl describe pod <pod-name> -n <namespace>
kubectl logs -f <pod-name> -c <container-name> -n <namespace>
kubectl exec -it <pod-name> -n <namespace> -- sh

```

### Helm Release Operations

```bash
helm repo add <repo-name> <repo-url>
helm repo update
helm install <release-name> ./<chart-directory> -f values.yaml -n <namespace> --create-namespace
helm upgrade --install <release-name> ./<chart-directory> -f values.yaml -n <namespace>
helm rollback <release-name> <revision> -n <namespace>
helm uninstall <release-name> -n <namespace>

```

### Docker Container Operations

```bash
# Lifecycle & Debugging
docker ps -a
docker logs -f <container-id>
docker exec -it <container-id> /bin/sh

# Compose Deployments
docker compose -f docker-compose.yml up -d
docker compose -f docker-compose.yml down -v

```

---

## 🎯 Lab Standard

Every lab folder strictly follows this layout:

```text
lab-name/
├── README.md        # Architecture, prerequisites, step-by-step verification
├── manifests/       # Raw YAML files (K8s, CRDs, Compose)
├── scripts/         # Bash automation (deploy, destroy, test)
└── diagrams/        # Architecture & request-flow diagrams

```

> **Engineering Principle:**
> Learn ➔ Deploy ➔ Break ➔ Troubleshoot ➔ Fix ➔ Verify

```

```
